import axios from "axios";
import {PackingList} from "../model/PackingList";
import {PackingItem} from "../model/PackingItem";

export function getAllPackingLists() {
    return axios.get<PackingList[]>("/api/packinglists")
        .then(response => response.data);
}

export function getPackingListById(id : string) {
    return axios.get<PackingList>(`/api/packinglists/${id}`)
        .then(response => response.data)
}

export function getPackingItemById(id: string, itemId: string) {
    return axios.get<PackingItem>(`/api/packinglists/${id}/packingitems/${itemId}`)
        .then(response => response.data)
}

export function postPackingList(newPackingList : Omit<PackingList, "id" | "dateOfArrival">) {
    return axios.post("/api/packinglists", newPackingList)
        .then(response => response.data);
}

export function putPackingList(id : string, editedPackingList : Omit<PackingList, "id" | "color">) {
    return axios.put(`/api/packinglists/${id}`, editedPackingList)
        .then(response => response.data);
}

export function addItemAndUpdateList(id: string, newPackingItem: Omit<PackingItem, "id">) {
    return axios.put(`/api/packinglists/${id}/packingitems`, newPackingItem)
        .then(response => response.data);
}

export function updateItemOfList(id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) {
    return axios.put(`/api/packinglists/${id}/packingitems/${itemId}`, updatedPackingItem)
        .then(response => response.data);
}

export function deletePackingListById(id: string) {
    return axios.delete(`/api/packinglists/${id}`)
}

export function deletePackingItemById(id: string, removeItemId: string) {
    return axios.delete(`/api/packinglists/${id}/packingitems/${removeItemId}`)
        .then(response => response.data)
}