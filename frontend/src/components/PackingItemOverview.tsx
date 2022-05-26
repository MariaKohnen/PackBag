import {PackingList} from "../model/PackingList";
import "./PackingItemOverview.css";

type PackingItemOverviewProps = {
    actualPackingList: PackingList;
}

export default function PackingItemOverview({actualPackingList}: PackingItemOverviewProps) {

    return (
        <div className="items-overview">
            <p>{actualPackingList.destination}</p>
        </div>
    )
}