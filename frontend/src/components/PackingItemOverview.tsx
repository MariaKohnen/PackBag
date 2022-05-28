import "./PackingItemOverview.css";
import {ItemOverview} from "./packingItemDetails/ItemOverview";
import {PackingItem} from "../model/PackingItem";

type PackingItemOverviewProps = {
    actualItemList: PackingItem[];
}

export default function PackingItemOverview({actualItemList}: PackingItemOverviewProps) {

    return (
        <div className="items-overview">
            <p>{actualItemList.map(item => <ItemOverview
                key={item.id}
                packingItem={item} />)
                .reverse()}</p>
        </div>
    )
}