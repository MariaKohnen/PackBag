import {createContext, ReactElement, useState} from "react";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const AUTH_KEY = "AuthToken"

export const AuthContext = createContext<{ token: string | undefined, logout: () => void, login: (credentials: { username: string, password: string }) => void, userRegistration: (credentials: { username: string, password: string }) => void }>(
    {
        token: undefined, logout: () => {
            toast.error("Logout is not initialized ")
        }, login: () => {
            toast.error("Login not initialized.")
        }, userRegistration: () => {
            toast.error("Registration not initialized")
        }
    })

export type AuthProviderProps = {
    children: ReactElement
}

export default function AuthProvider({children}: AuthProviderProps) {

    const [token, setToken] = useState<string | undefined>(localStorage.getItem(AUTH_KEY) ?? undefined)
    const navigate = useNavigate()

    const login = (credentials: { username: string, password: string }) => {
        axios.post("/auth/login", credentials)
            .then(response => response.data)
            .then(newToken => {
                setToken(newToken)
                localStorage.setItem(AUTH_KEY, newToken)
            })
            .then(() => navigate("/"))
            .catch(() => toast.error("Login failed. Username or password were not correct"))
    }

    const userRegistration = (credentials: { username: string, password: string }) => {
            axios.post("auth/registration", credentials)
                .then(response => response.data)
                .then(newToken => {
                    setToken(newToken)
                    localStorage.setItem(AUTH_KEY, newToken)
                })
                .then(() => navigate("/"))
                .catch(exception => toast.error(exception + "Login failed"))
        }

        const logout = () => {
            localStorage.removeItem("AuthToken")
            setToken("")
        }

    return (
        <div>
            <AuthContext.Provider value={{token, logout, login, userRegistration}}>
                {children}
            </AuthContext.Provider>
        </div>
    )
}
