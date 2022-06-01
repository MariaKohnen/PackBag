import "./styling/PackingItemOverview.css";
import {ItemCard} from "./ItemCard";
import {PackingItem} from "../../model/PackingItem";
import AddItemToPackingList from "./AddItemToPackingList";

type PackingItemOverviewProps = {
    actualItemList: PackingItem[] | undefined;
    addItemToPackingList: (id: string, newPackingItem: Omit<PackingItem, "id">) => void
    id: string
    deleteItem: (id: string, itemId: string) => void
}

export default function PackingItemOverview({actualItemList, addItemToPackingList, id, deleteItem}: PackingItemOverviewProps) {

    return (
        <div className="items-overview">
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
                    .reverse()}</p>
                }
            </div>
        </div>
    )
}