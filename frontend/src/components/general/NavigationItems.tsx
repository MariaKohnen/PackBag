import {NavigationBarData} from "../../data/NavigationBarData";
import {Link} from "react-router-dom";
import "./styling/NavigationItems.css";

export default function NavigationItems() {
    return (
        <div className="items-nav">
            {NavigationBarData.map((item, index) => <div key={index} className={item.cName}>
                <Link to={item.path}>
                    {item.icon}
                </Link>
            </div>)}
        </div>
    )
}
