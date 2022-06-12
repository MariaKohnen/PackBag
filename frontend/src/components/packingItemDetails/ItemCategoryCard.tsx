import {PackingItem} from "../../model/PackingItem";
import {ItemCard} from "./ItemCard";
import "./styling/ItemCategoryCard.css";
import React, {useState} from "react";
import {GrDown, GrUp} from "react-icons/gr";

type ItemOverviewProps = {
    filteredItems: PackingItem[]
    deleteItem: (id: string, itemId: string) => void
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
    category: string
}

export function ItemCategoryCard({filteredItems, deleteItem, id, updateItemAndGetUpdatedList, category}: ItemOverviewProps) {

    const [open, setOpen] = useState<boolean>(true)

    const toggle = () => setOpen(!open)

    return (
        <div className="item-category-card">
            <div className="dd-category">
            <p id={category}>{category}</p>
            <div role="button" onClick={() => toggle()}>
                {open ? <GrUp/>
                    : <GrDown/>}
            </div>
            </div>
            {open && filteredItems && filteredItems.map(item =>
                <ItemCard
                    key={item.id}
                    packingItem={item}
                    deleteItem={deleteItem}
                    updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}
                    id={id}/>)
                .reverse()}
        </div>
    )
}
