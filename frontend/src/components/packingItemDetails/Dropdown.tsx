import {useState} from "react";
import "./styling/Dropdown.css";
import {GrDown, GrUp} from "react-icons/gr";

type DropdownProps = {
    status: { id: number, value: string, icon: JSX.Element }[]
    newStatus: { id: number, value: string } | undefined
    setNewStatus: (status : { id: number, value: string, icon: JSX.Element }) => void
}

export default function Dropdown({status, newStatus, setNewStatus}: DropdownProps) {
    const [open, setOpen] = useState<boolean>(false);

    const toggle = () => setOpen(!open);

    const handleOnClick = (item: { id: number, value: string, icon: JSX.Element }) => {
        if (status.map(current => current.id === item.id)) {
            setNewStatus(item);
            toggle();
        } else return
    }

    return (
        <div className="dd-wrapper">
            <p>Choose a state for item:</p>
            <div
                className="dd-header"
                tabIndex={0}
                role="button"
                onClick={() => toggle()}>
                <div className="dd-header-title">
                    <p>{newStatus && newStatus.value}</p>
                </div>
                <div className="dd-header-action"><p>{open ? < GrUp /> : <GrDown />}</p></div>
            </div>
            {open && (
                <ul className="dd-list">
                    {status.map(item => (
                            <li className="dd-list-item" key={item.id}>
                                <button type="button" onClick={() => handleOnClick(item)}>
                                    {item.icon}
                                    <span>{item.value}</span>
                                </button>
                            </li>
                        )
                    )}
                </ul>
            )}
        </div>
    )
}