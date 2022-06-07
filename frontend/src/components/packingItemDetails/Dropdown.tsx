import {useState} from "react";
import "./styling/Dropdown.css";
import {GrDown, GrUp} from "react-icons/gr";

type DropdownProps = {
    status: { id: number, value: string, icon: JSX.Element }[]
    newStatus: { id: number, value: string } | undefined
    setNewStatus: (status: { id: number, value: string, icon: JSX.Element }) => void
    setButtonText: (buttonText: string) => void
}

export default function Dropdown({status, newStatus, setNewStatus, setButtonText}: DropdownProps) {
    const [open, setOpen] = useState<boolean>(false);

    const toggle = () => setOpen(!open);

    const handleOnClick = (item: { id: number, value: string, icon: JSX.Element }) => {
        if (status.map(current => current.id === item.id)) {
            setNewStatus(item);
            setButtonText("confirm")
            toggle();
        } else return
    }

    return (
        <div className="dd-wrapper">
            <div className="dd-header"
                 role="button"
                 onClick={() => toggle()}>
                <div className="dd-header-title">
                    {newStatus && newStatus.value}
                </div>
                <div className="dd-header-action">
                    {open ?
                        < GrUp/>
                        : <GrDown/>}
                </div>
            </div>
            {open && (
                <ul className="dd-list">
                    {status.map(item => (
                            <li className="dd-list-item" key={item.id}>
                                <button type="button" onClick={() => handleOnClick(item)}>
                                    {item.icon}
                                    {item.value}
                                </button>
                            </li>))}
                </ul>)}
        </div>
    )
}
