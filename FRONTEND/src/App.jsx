import './App.css'
import Nav from "./components/partials/nav.jsx";
import {Outlet} from "react-router-dom";
import Breadcrumbs from "./components/partials/breadcrumbs.jsx";

function App() {
    return (
        <>
            <Nav/>
            <div className="container mx-auto">
                <Breadcrumbs/>
                <Outlet/>
            </div>
        </>
    )
}

export default App
