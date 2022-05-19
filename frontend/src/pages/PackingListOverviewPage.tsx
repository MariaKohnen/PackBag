import usePackingLists from "../hooks/usePackingLists";
import {Header} from "../components/Header";
import PackingListTile from "../components/PackingListTile";

export default function PackingListOverviewPage() {

    const {packingLists} = usePackingLists();

    return (
        <div>
            <div>
                <Header/>
            </div>
            <div>
                <div>
                    {packingLists.map(list => <PackingListTile
                        key={list.id}
                        packingList={list}/>)}
                </div>
            </div>
        </div>
    )
}