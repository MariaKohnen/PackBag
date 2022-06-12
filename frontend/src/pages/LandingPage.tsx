import {useNavigate} from "react-router-dom";
import "./LandingPage.css";

export default function LandingPage() {

    const navigate = useNavigate()

    return (
        <div className="landing-page-container">
            <h1>hello</h1>
            <h1>again</h1>
            <button className="form-button" type="submit" onClick={() => navigate("/login")}>Login</button>
            <button className="form-button" type="submit" onClick={() => navigate("/registration")}>Sign up</button>
        </div>
    )
}