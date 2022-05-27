import {HeaderOverview} from "../components/general/HeaderOverview";
import PackingListTile from "../components/PackingListTile";
import "./PackingListOverviewPage.css";
import AddNewPackingList from "../components/AddNewPackingList";
import {PackingList} from "../model/PackingList";

type PackingListOverviewPageProps = {
    packingLists : PackingList[]
    addPackingList : (newPackingList: Omit<PackingList, "id" | "dateOfArrival">) => void
    deletePackingList : (id: string) => void
}

export default function PackingListOverviewPage({packingLists, addPackingList, deletePackingList} : PackingListOverviewPageProps) {

    return (
        <div>
            <div>
                <HeaderOverview />
            </div>
                <div>
                    <AddNewPackingList
                    addPackingList={addPackingList}/>
                </div>
                <div className="packing-list-overview">
                    {packingLists.map(list => <PackingListTile
                        key={list.id}
                        packingList={list}
                        deletePackingList={deletePackingList} />)
                        .reverse()}
            </div>
        </div>
    )
}