import {useState} from "react";
import {IconContext} from "react-icons";
import {Link} from "react-router-dom";
import {AiOutlineLine, AiOutlinePlus} from "react-icons/ai";
import {NavigationBarData} from "./NavigationBarData";
import "./NavigationBar.css";

export default function NavigationBar() {

    const [openNavBar, setOpenNavBar] = useState(false);

    const openSidebar = () => setOpenNavBar(!openNavBar);


    return(
        <div className="nav-container">
            <IconContext.Provider value={{ color: 'white'}}>
                <div className="navbar">
                    <Link to='#' className="menu-bars">
                        <AiOutlineLine onClick={openSidebar} />
                    </Link>
                </div>
                <nav className={openNavBar? 'nav-menu active' : 'nav-menu'}>
                    <ul className="nav-menu-items" onClick={openSidebar}>
                        <li className="navbar-toogle">
                            <Link to={"#"} className="menu-bars">
                                <AiOutlinePlus onClick={openSidebar} />
                            </Link>
                        </li>
                        {NavigationBarData.map((item, index) => {
                            return (
                                <li key={index} className={item.cName}>
                                    <Link to={item.path}>
                                        {item.icon}
                                        <span>{item.title}</span>
                                    </Link>
                                </li>
                            );
                        })}
                    </ul>
                </nav>
            </IconContext.Provider>
        </div>
    )
}