import axios from "axios";
import {PackingList} from "../model/PackingList";

export function getAllPackingLists() {
    return axios.get<PackingList[]>("/api/packinglists")
        .then(response => response.data);
}

export function postPackingList(newPackingList : Omit<PackingList, "id">) {
    return axios.post("/api/packinglists", newPackingList)
        .then(response => response.data);
}