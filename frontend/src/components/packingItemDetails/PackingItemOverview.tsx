import "./styling/PackingItemOverview.css";
import {ItemCard} from "./ItemCard";
import {PackingItem} from "../../model/PackingItem";
import AddItemToPackingList from "./AddItemToPackingList";

type PackingItemOverviewProps = {
    actualItemList: PackingItem[];
}

export default function PackingItemOverview({actualItemList}: PackingItemOverviewProps) {

    return (
        <div>
            <div>
                <AddItemToPackingList />
            </div>
            <div className="items-overview">
                <p>{actualItemList.map(item => <ItemCard
                    key={item.id}
                    packingItem={item}/>)
                    .reverse()}</p>
            </div>
        </div>
    )
}