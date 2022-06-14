import {PackingItem} from "../../model/PackingItem";
import React, {useEffect, useState} from "react";
import {AiOutlineMinus, AiOutlinePlus} from "react-icons/ai";
import {IconContext} from "react-icons";
import "./styling/ItemFilter.css";

type FilteredItemProps = {
    actualItemList: PackingItem[] | undefined
    setFilteredItems: (filteredItems: PackingItem[] | undefined) => void
    filteredItems: PackingItem[] | undefined
    setFilterText: (text: string) => void
}

export default function ItemFilter({actualItemList, setFilteredItems, filteredItems, setFilterText}: FilteredItemProps) {

    const [open, setOpen] = useState<boolean>(false)
    const [newFilter, setNewFilter] = useState("")

    const toggle = () => setOpen(!open)

    useEffect(() => {
        const updatedFilterItems = filteredItems?.filter(item => {
            return actualItemList?.includes(item)
        })
        setFilteredItems(updatedFilterItems)
        // eslint-disable-next-line
    }, [actualItemList])

    useEffect(() => {
        if (newFilter === "") {
            return
        }
        newFilter === "show all" ?
            actualItemList && setFilteredItems(actualItemList)
            : actualItemList && setFilteredItems(actualItemList.filter(item => item.status === newFilter))
        setFilterText(newFilter)
        open && toggle()
        //eslint-disable-next-line
    }, [actualItemList, newFilter])

    const handleResetFilter = () => {
        setFilteredItems(undefined)
        toggle()
    }

    const filterOption = ["show all", "Open", "To be done", "Packed"]

    return (
        <IconContext.Provider value={{color: '#465556'}}>
            <div className="dd-filter-wrapper">
                <div role="button" onClick={() => toggle()}><p>Filter</p>
                    {open ? <AiOutlineMinus/>
                        : <AiOutlinePlus/>}
                </div>
                {open &&
                    <div className="dd-filter-buttons">
                        {filterOption.map(filter => <button type="submit"
                                                            key={filter.toString()}
                                                            onClick={() => setNewFilter(filter)}>{filter}</button>)}
                        <button type="submit" onClick={handleResetFilter}>Reset</button>
                    </div>
                }
            </div>
        </IconContext.Provider>
    )
}
