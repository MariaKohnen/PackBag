import { PackingItem } from "./PackingItem";

export type PackingList = {

    id: string;
    destination: string;
    dateOfArrival: Date;
    packingItemList?: PackingItem[]
    color: string
}