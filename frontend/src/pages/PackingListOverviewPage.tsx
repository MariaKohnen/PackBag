import usePackingLists from "../hooks/usePackingLists";
import {HeaderOverview} from "../components/HeaderOverview";
import PackingListTile from "../components/PackingListTile";
import "./PackingListOverviewPage.css";
import AddNewPackingList from "../components/AddNewPackingList";
import useDetailedPackingList from "../hooks/useDetailedPackingList";
import {useNavigate} from "react-router-dom";

export default function PackingListOverviewPage() {

    const {packingLists, addPackingList} = usePackingLists();
    const {detailedPackingList} = useDetailedPackingList();
    const navigate = useNavigate()

    const onPackingListClick = () => {
        if (!detailedPackingList ) {
            return }
            navigate(`/packinglist/${detailedPackingList.id}`)
    }

    return (
        <div>
            <div>
                <HeaderOverview/>
            </div>
                <div>
                    <AddNewPackingList
                    addPackingList={addPackingList}/>
                </div>
            <button onClick={onPackingListClick}>
                <div className="packing-list-overview">
                    {packingLists.map(list => <PackingListTile
                        key={list.id}
                        packingList={list}/>)
                        .reverse()}
            </div>
            </button>
        </div>
    )
}