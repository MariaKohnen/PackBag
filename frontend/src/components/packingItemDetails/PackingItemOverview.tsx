import "./styling/PackingItemOverview.css";
import {ItemCard} from "./ItemCard";
import {PackingItem} from "../../model/PackingItem";
import AddItemToPackingList from "./AddItemToPackingList";
import EditPackingItem from "./EditPackingItem";
import {Routes, Route} from "react-router-dom";

type PackingItemOverviewProps = {
    actualItemList: PackingItem[] | undefined;
    addItemToPackingList: (id: string, newPackingItem: Omit<PackingItem, "id" | "status">) => void
    id: string
    deleteItem: (id: string, itemId: string) => void
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
}

export default function PackingItemOverview({actualItemList, addItemToPackingList, id, deleteItem, updateItemAndGetUpdatedList}: PackingItemOverviewProps) {

    return (
        <div className="items-overview">
            <Routes>
                <Route path={`packingitems/:itemId`} element={<EditPackingItem
                    updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}
                    id={id}/>}/>
                <Route index element={
                    <div>
                        <div>
                            <AddItemToPackingList
                            addItemToPackingList={addItemToPackingList}
                            id={id}/>
                        </div>
                        <div className="item-container">
                            <p>your packing list</p>
                            {actualItemList &&
                                <p className="category-text">{actualItemList.map(item => <ItemCard
                                    key={item.id}
                                    packingItem={item}
                                    deleteItem={deleteItem}
                                    id={id}/>)
                                    .reverse()}</p>}
                        </div>
                    </div>}
                />
            </Routes>
        </div>
    )
}