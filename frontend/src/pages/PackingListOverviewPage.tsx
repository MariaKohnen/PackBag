import usePackingLists from "../hooks/usePackingLists";
import {HeaderOverview} from "../components/HeaderOverview";
import PackingListTile from "../components/PackingListTile";
import "./PackingListOverviewPage.css";

export default function PackingListOverviewPage() {

    const {packingLists} = usePackingLists();

    return (
        <div>
            <div>
                <HeaderOverview/>
            </div>
            <div>
                <div className="packing-list-overview">
                    {packingLists.map(list => <PackingListTile
                        key={list.id}
                        packingList={list}/>)}
                </div>
            </div>
        </div>
    )
}