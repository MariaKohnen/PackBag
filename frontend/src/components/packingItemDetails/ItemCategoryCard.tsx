import {PackingItem} from "../../model/PackingItem";
import {ItemCard} from "./ItemCard";
import "./styling/ItemCategoryCard.css";

type ItemOverviewProps = {
    filteredItems: PackingItem[]
    deleteItem: (id: string, itemId: string) => void
    id: string
    category: string
}

export function ItemCategoryCard({filteredItems, deleteItem, id, category}: ItemOverviewProps) {
    return (
        <div className="item-category-card">
            <p id={category}>{category ?
                category
                : "no category"}</p>
            {filteredItems && filteredItems.map(item =>
                <ItemCard
                    key={item.id}
                    packingItem={item}
                    deleteItem={deleteItem}
                    id={id}/>)
                .reverse()}
        </div>
    )
}