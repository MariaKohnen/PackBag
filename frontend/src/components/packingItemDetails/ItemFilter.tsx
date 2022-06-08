import {PackingItem} from "../../model/PackingItem";
import React, {useEffect, useState} from "react";
import {AiOutlineMinus, AiOutlinePlus} from "react-icons/ai";
import {IconContext} from "react-icons";
import "./styling/ItemFilter.css";

type FilteredItemProps = {
    actualItemList: PackingItem[] | undefined
    setFilter: (filteredItems: PackingItem[] | undefined) => void
    filteredItems: PackingItem[] | undefined
    setFilterText: (text: string) => void
}

export default function ItemFilter({actualItemList, setFilter, filteredItems, setFilterText}: FilteredItemProps) {

    const [open, setOpen] = useState<boolean>(false)

    const toggle = () => setOpen(!open)

    useEffect(() => {
        const updatedFilterItems = filteredItems?.filter(item => {
           return actualItemList?.includes(item)})
        setFilter(updatedFilterItems)
        // eslint-disable-next-line
    }, [actualItemList])

    const handleFilterAll = () => {
        actualItemList && setFilter(actualItemList)
        setFilterText("show all")
        toggle()
    }

    const handleFilterStatusOpen = () => {
        actualItemList && setFilter(actualItemList.filter(item => item.status === "Open"))
        setFilterText("Open")
        toggle()
    }

    const handleFilterStatusToDo = () => {
        actualItemList && setFilter(actualItemList.filter(item => item.status === "To be done"))
        setFilterText("To be done")
        toggle()
    }

    const handleFilterStatusDone = () => {
        actualItemList && setFilter(actualItemList.filter(item => item.status === "Done"))
        setFilterText("Done")
        toggle()
    }

    const handleResetFilter = () => {
        setFilter(undefined)
        toggle()
    }

    return (
        <IconContext.Provider value={{color: '#465556'}}>
            <div className="dd-filter-wrapper">
                <div role="button" onClick={() => toggle()}>Filter
                    {open ? <AiOutlineMinus/>
                        : <AiOutlinePlus/>}
                </div>
                {open &&
                    <div className="dd-filter-buttons">
                        <button type="submit" onClick={handleFilterAll}>show all</button>
                        <button type="submit" onClick={handleFilterStatusOpen}> Open</button>
                        <button type="submit" onClick={handleFilterStatusToDo}> To be done</button>
                        <button type="submit" onClick={handleFilterStatusDone}> Done</button>
                        <button type="submit" onClick={handleResetFilter}>Reset</button>
                    </div>
                }
            </div>
        </IconContext.Provider>
    )
}