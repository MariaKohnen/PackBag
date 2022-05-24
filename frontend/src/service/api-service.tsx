import axios from "axios";
import {PackingList} from "../model/PackingList";

export function getAllPackingLists() {
    return axios.get<PackingList[]>("/api/packinglists")
        .then(response => response.data);
}

export function getPackingListById(id : string) {
    return axios.get<PackingList>(`/api/packinglists/${id}`)
        .then(response => response.data)
}

export function postPackingList(newPackingList : Omit<PackingList, "id" | "dateOfArrival">) {
    return axios.post("/api/packinglists", newPackingList)
        .then(response => response.data);
}

export function putPackingList(editedPackingList : Omit<PackingList, "id">) {
    return axios.put("/api/packinglists", editedPackingList)
        .then(response => response.data);
}