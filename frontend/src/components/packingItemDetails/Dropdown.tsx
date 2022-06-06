import {useState} from "react";
import "./styling/Dropdown.css";

type DropdownProps = {
    status: { id: number, value: string }[]
    selection: { id: number, value: string } | undefined
    setSelection: (status : { id: number, value: string }) => void
}

export default function Dropdown({status, selection, setSelection}: DropdownProps) {
    const [open, setOpen] = useState<boolean>(false);

    const toggle = () => setOpen(!open);

    const handleOnClick = (item: { id: number, value: string }) => {
        if (status.map(current => current.id === item.id)) {
            setSelection(item);
        } else return
    }

    return (
        <div className="dd-wrapper">
            <p>Set a state:</p>
            <div
                className="dd-header"
                tabIndex={0}
                role="button"
                onClick={() => toggle()}>
                <div className="dd-header-title">
                    <p>{selection && selection.value}</p>
                </div>
                <div className="dd-header-action"><p>{open ? "Close" : "Open"}</p></div>
            </div>
            {open && (
                <ul className="dd-list">
                    {status.map(item => (
                            <li className="dd-list-item" key={item.id}>
                                <button type="button" onClick={() => handleOnClick(item)}>
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