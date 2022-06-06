import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";
import PackingListDetailsPage from "./pages/PackingListDetailsPage";
import NavigationBar from "./components/general/NavigationBar";
import usePackingLists from "./hooks/usePackingLists";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

export default function App() {

    const {packingLists, addPackingList, updatePackingList, deletePackingList, addNewItem, deletePackingItem, updatePackingItem} = usePackingLists();

    return (
        <HashRouter>
            <div className="App">
                <ToastContainer
                    className="error-warning"
                    position="top-center"
                    autoClose={1500}
                    hideProgressBar={true}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss={false}
                    draggable
                    pauseOnHover
                />
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage
                               packingLists={packingLists}
                               addPackingList={addPackingList}
                               deletePackingList={deletePackingList}/>}/>
                    <Route path={`/packinglist/:id/*`}
                           element={<PackingListDetailsPage
                               updatePackingList={updatePackingList}
                               addNewItem={addNewItem}
                               deletePackingItem={deletePackingItem}
                               updatePackingItem={updatePackingItem}/>} />
                </Routes>
                <NavigationBar/>
            </div>
        </HashRouter>
    )
}