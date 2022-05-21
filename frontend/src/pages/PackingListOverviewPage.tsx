import usePackingLists from "../hooks/usePackingLists";
import {HeaderOverview} from "../components/HeaderOverview";
import PackingListTile from "../components/PackingListTile";
import "./PackingListOverviewPage.css";
import AddNewPackingList from "../components/AddNewPackingList";

export default function PackingListOverviewPage() {

    const {packingLists, addPackingList} = usePackingLists();

    return (
        <div>
            <div>
                <HeaderOverview/>
            </div>
                <div>
                    <AddNewPackingList
                    addPackingList={addPackingList}/>
                </div>
                <div className="packing-list-overview">
                    {packingLists.map(list => <PackingListTile
                        key={list.id}
                        packingList={list}/>)}
            </div>
        </div>
    )
}