import "./styling/PackingItemOverview.css";
import {PackingItem} from "../../model/PackingItem";
import AddItemToPackingList from "./AddItemToPackingList";
import EditPackingItem from "./EditPackingItem";
import {Routes, Route} from "react-router-dom";
import {ItemCategoryCard} from "./ItemCategoryCard";
import {useEffect, useState} from "react";
import {ItemCard} from "./ItemCard";
import ItemFilter from "./ItemFilter";

type PackingItemOverviewProps = {
    actualItemList: PackingItem[] | undefined;
    addItemToPackingList: (id: string, newPackingItem: Omit<PackingItem, "id">) => void
    id: string
    deleteItem: (id: string, itemId: string) => void
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
}

export default function PackingItemOverview({
                                                actualItemList,
                                                addItemToPackingList,
                                                id,
                                                deleteItem,
                                                updateItemAndGetUpdatedList
                                            }: PackingItemOverviewProps) {

    const [categorizedItems, setCategorizedItems] = useState<Map<string, PackingItem[]>>(new Map())
    const [filteredItems, setFilteredItems] = useState<PackingItem[] | undefined>()
    const [newFilter, setNewFilter] = useState("")

    useEffect(() => {
        const itemsByCategory = new Map<string, PackingItem[]>()
        actualItemList?.forEach(item => {
            let packingItemsOfCurrentCategory = itemsByCategory.get(item.category)
            if (!packingItemsOfCurrentCategory) {
                packingItemsOfCurrentCategory = []
                itemsByCategory.set(item.category, packingItemsOfCurrentCategory)
            }
            packingItemsOfCurrentCategory.push(item)
        })
        setCategorizedItems(itemsByCategory)
    }, [actualItemList])

    const handleResetFilter = () => {
        setFilteredItems(undefined)
        setNewFilter("")
    }

    return (
        <div className="items-overview">
            <Routes>
                <Route path={`packingitems/:itemId`} element={<EditPackingItem
                    updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}
                    id={id}/>}/>
                <Route index element={
                    <div>
                        <AddItemToPackingList
                            addItemToPackingList={addItemToPackingList}
                            id={id}/>

                        <ItemFilter
                            actualItemList={actualItemList}
                            setFilteredItems={setFilteredItems}
                            filteredItems={filteredItems}
                            newFilter={newFilter}
                            setNewFilter={setNewFilter}/>

                        {filteredItems ?
                            <div className="item-container">
                                <div className="item-container-header">
                                    <p>Filter: {newFilter} </p>
                                    <button onClick={handleResetFilter}>reset</button>
                                </div>
                                {filteredItems && filteredItems.map(item =>
                                    <ItemCard
                                        key={item.id}
                                        packingItem={item}
                                        deleteItem={deleteItem}
                                        updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}
                                        id={id}/>)
                                    .reverse()}
                            </div>
                            : <div className="item-container">
                                {Array.from(categorizedItems?.keys()).map(key =>
                                    <ItemCategoryCard
                                        key={key}
                                        category={key}
                                        filteredItems={categorizedItems?.get(key)!}
                                        updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}
                                        deleteItem={deleteItem}
                                        id={id}/>)}
                            </div>
                        }
                    </div>}/>
            </Routes>
        </div>
    )
}
