import "./PackingItemOverview.css";
import {ItemCard} from "./packingItemDetails/ItemCard";
import {PackingItem} from "../model/PackingItem";

type PackingItemOverviewProps = {
    actualItemList: PackingItem[];
}

export default function PackingItemOverview({actualItemList}: PackingItemOverviewProps) {

    return (
        <div className="items-overview">
            <p>{actualItemList.map(item => <ItemCard
                key={item.id}
                packingItem={item} />)
                .reverse()}</p>
        </div>
    )
}