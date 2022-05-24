import {HeaderOverview} from "../components/HeaderOverview";
import PackingListTile from "../components/PackingListTile";
import "./PackingListOverviewPage.css";
import AddNewPackingList from "../components/AddNewPackingList";
import {PackingList} from "../model/PackingList";

type PackingListOverviewPageProps = {
    packingLists : PackingList[]
    addPackingList : (newPackingList: Omit<PackingList, "id" | "dateOfArrival">) => void
}

export default function PackingListOverviewPage({packingLists, addPackingList} : PackingListOverviewPageProps) {

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
                        packingList={list}/>)
                        .reverse()}
            </div>
        </div>
    )
}