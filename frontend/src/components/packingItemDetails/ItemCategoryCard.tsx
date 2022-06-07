import {PackingItem} from "../../model/PackingItem";
import {ItemCard} from "./ItemCard";

type ItemOverviewProps = {
    filteredItems: PackingItem[]
    deleteItem: (id: string, itemId: string) => void
    id: string
    category: string
}

export function ItemCategoryCard({filteredItems, deleteItem, id, category}: ItemOverviewProps) {
    return (
        <div>
            <p>{category}</p>
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