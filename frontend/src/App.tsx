import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";
import PackingListDetailsPage from "./pages/PackingListDetailsPage";
import NavigationBar from "./components/general/NavigationBar";
import usePackingLists from "./hooks/usePackingLists";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import LoginPage from "./pages/LoginPage";
import RequireAuth from "./routing/RequireAuth";
import RegistrationPage from "./pages/RegistrationPage";
import LandingPage from "./pages/LandingPage";

export default function App() {

    const {packingLists, addPackingList, updatePackingList, deletePackingList, addNewItem, deletePackingItem, updatePackingItem} = usePackingLists();

    return (
        <div className="App">
            <ToastContainer
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
                <Route path={'/hello'} element={<LandingPage />} />
                <Route path={'/login'} element={<LoginPage />}/>
                <Route path={'/registration'} element={<RegistrationPage/>}/>
                <Route element={<RequireAuth/>}>
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
                               updatePackingItem={updatePackingItem}/>}/>
                </Route>
            </Routes>
            <NavigationBar/>
        </div>
    )
}