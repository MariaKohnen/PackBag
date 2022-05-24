import {useState} from "react";
import {IconContext} from "react-icons";
import {Link} from "react-router-dom";
import {AiOutlineLine, AiOutlinePlus} from "react-icons/ai";
import {NavigationBarData} from "./NavigationBarData";
import "./NavigationBar.css";

export default function NavigationBar() {

    const [openNavBar, setOpenNavBar] = useState(false);

    const openSidebar = () => setOpenNavBar(!openNavBar);


    return (
        <IconContext.Provider value={{color: '#eaeadf'}}>
            <div className="nav-container">
                <div className="navbar">
                    <Link to='#' className="menu-open">
                        <AiOutlinePlus onClick={openSidebar}/>
                    </Link>
                </div>
                <nav className={openNavBar ? 'nav-menu active' : 'nav-menu'}>
                    <div className="nav-menu-items" onClick={openSidebar}>
                        <div className="navbar-toogle">
                            <Link to={"#"} className="menu-close">
                                <AiOutlineLine onClick={openSidebar}/>
                            </Link>
                        </div>
                        {NavigationBarData.map((item, index) => {
                            return (
                                <div key={index} className={item.cName}>
                                    <Link to={item.path}>
                                        {item.icon}
                                    </Link>
                                </div>
                            );
                        })}
                    </div>
                </nav>
            </div>
        </IconContext.Provider>
    )
}