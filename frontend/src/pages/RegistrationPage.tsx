import {FormEvent, useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import AuthHeader from "../components/general/AuthHeader";
import "./LoginPage.css";
import ReturnButton from "../components/general/ReturnButton";
import {useNavigate} from "react-router-dom";

export default function RegistrationPage() {

    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const navigate = useNavigate()

    const {userRegistration} = useContext(AuthContext)

    const onSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        userRegistration({
                username: username,
                password: password
            }
        )
    }

    return (
        <div>
            <ReturnButton />
            <AuthHeader/>
            <form className="auth-container" onSubmit={onSubmit}>
                <h2>Registration</h2>
                <input className="text-field"
                       type={"text"}
                       value={username}
                       placeholder={"Enter your username"}
                       onChange={event => setUsername(event.target.value)}/>
                <input className="text-field"
                       type={"password"}
                       value={password}
                       placeholder={"Enter your password"}
                       onChange={event => setPassword(event.target.value)}/>
                <button className="form-button"
                        type={"submit"}>Sign Up
                </button>
            </form>
            <button className="text-button"
                    type="submit"
                    onClick={() => navigate("/registration")}>
                You have an account already? <b>Login here!</b>
            </button>
        </div>
    )
}
