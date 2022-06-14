import {useState} from "react";
import {IconContext} from "react-icons";
import {Link} from "react-router-dom";
import {AiOutlineLine} from "react-icons/ai";
import "./styling/NavigationBar.css";
import NavigationItems from "./NavigationItems";
import {GiHamburgerMenu} from "react-icons/gi";

export default function NavigationBar() {

    const [openNavBar, setOpenNavBar] = useState(false)

    const openSidebar = () => setOpenNavBar(!openNavBar)

    return (
        <IconContext.Provider value={{color: '#eaeadf'}}>
            <div className="nav-container">
                <div className="navbar">
                    <Link to='#' className="menu-open">
                        <GiHamburgerMenu onClick={openSidebar}/>
                    </Link>
                </div>
                <nav className={openNavBar ? 'nav-menu active' : 'nav-menu'}>
                    <div className="nav-menu-items" onClick={openSidebar}>
                        <div className="navbar-toogle">
                            <Link to={"#"} className="menu-close">
                                <AiOutlineLine onClick={openSidebar}/>
                            </Link>
                        </div>
                            <NavigationItems />
                    </div>
                </nav>
            </div>
        </IconContext.Provider>
    )
}
