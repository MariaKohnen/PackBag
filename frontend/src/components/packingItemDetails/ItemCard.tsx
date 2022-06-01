import { IconContext } from "react-icons";
import {PackingItem} from "../../model/PackingItem";
import {BsCircle} from "react-icons/bs";
import "./styling/ItemCard.css";

type ItemOverviewProps = {
    packingItem: PackingItem
}

export function ItemCard({packingItem}: ItemOverviewProps) {
    return(
        <IconContext.Provider value={{color: '#d7a36f'}}>
        <div className="item-card">
            <BsCircle />
            <p>{packingItem.name}</p>
        </div>
        </IconContext.Provider>
    )
}