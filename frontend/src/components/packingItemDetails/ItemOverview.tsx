import {PackingItem} from "../../model/PackingItem";

type ItemOverviewProps = {
    packingItem: PackingItem
}

export function ItemOverview({packingItem}: ItemOverviewProps) {
    return(
        <div>
            <p>{packingItem.name}</p>
        </div>
    )
}