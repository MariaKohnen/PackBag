import {useNavigate} from "react-router-dom";
import {MouseEventHandler} from "react";
import {MdArrowBackIosNew} from "react-icons/md";
import { IconContext } from "react-icons";
import "./styling/ReturnButton.css";

export default function ReturnButton() {

    const navigate = useNavigate()

    const handleClick: MouseEventHandler<HTMLButtonElement> = () => {
        navigate(-1)
    }

    return (
        <IconContext.Provider value={{color: '#eaeadf'}}>
        <div className="return-button-container">
            <button onClick={handleClick}><MdArrowBackIosNew /></button>
        </div>
        </IconContext.Provider>
    )
}