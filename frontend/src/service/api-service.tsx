import axios from "axios";
import {PackingList} from "../model/PackingList";
import {PackingItem} from "../model/PackingItem";

export function getAllPackingLists(token?: string) {
    return axios.get<PackingList[]>("/api/packinglists", token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function getPackingListById(id: string, token?: string) {
    return axios.get<PackingList>(`/api/packinglists/${id}`, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function getPackingItemById(id: string, itemId: string, token?: string) {
    return axios.get<PackingItem>(`/api/packinglists/${id}/packingitems/${itemId}`, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function postPackingList(newPackingList: Omit<PackingList, "id" | "dateOfArrival">, token?: string) {
    return axios.post("/api/packinglists", newPackingList, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function putPackingList(id: string, editedPackingList: Omit<PackingList, "id" | "color">, token?: string) {
    return axios.put(`/api/packinglists/${id}`, editedPackingList, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function addItemAndUpdateList(id: string, newPackingItem: Omit<PackingItem, "id">, token?: string) {
    return axios.put(`/api/packinglists/${id}/packingitems`, newPackingItem, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function updateItemOfList(id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">, token?: string) {
    return axios.put(`/api/packinglists/${id}/packingitems/${itemId}`, updatedPackingItem, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export function deletePackingListById(id: string, token?: string) {
    return axios.delete(`/api/packinglists/${id}`, token
        ? {headers: {"Authorization": token}}
        : {})
}

export function deletePackingItemById(id: string, removeItemId: string, token?: string) {
    return axios.delete(`/api/packinglists/${id}/packingitems/${removeItemId}`, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}
