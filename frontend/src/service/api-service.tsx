import axios from "axios";
import {PackingList} from "../model/PackingList";

export function getAllPackingLists() {
    return axios.get<PackingList[]>("/api/packinglists")
        .then(response => response.data);
}