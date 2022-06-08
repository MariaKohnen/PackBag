import {useState} from "react";
import "./styling/Dropdown.css";
import {GrDown, GrUp} from "react-icons/gr";

type CategoryDropdownProps = {
    categories: string[]
    newCategory: string
    setNewCategory: (newCategory: string) => void
    setButtonText: (buttonText: string) => void
}

export default function CategoryDropdown({categories, newCategory, setNewCategory, setButtonText}: CategoryDropdownProps) {
    const [open, setOpen] = useState<boolean>(false)

    const toggle = () => setOpen(!open);

    const handleOnClick = (item: string) => {
        if (categories.map(current => current === item)) {
            setNewCategory(item)
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
                    {newCategory}
                </div>
                <div className="dd-header-action">
                    {open ?
                        < GrUp/>
                        : <GrDown/>}
                </div>
            </div>
            {open && (
                <ul className="dd-list">
                    {categories.map(item => (
                            <li className="dd-list-item" key={item}>
                                <button type="button" onClick={() => handleOnClick(item)}>
                                    {item}
                                </button>
                            </li>))}
                </ul>)}
        </div>
    )
}
