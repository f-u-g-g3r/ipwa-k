import './App.css'
import Nav from "./components/partials/nav.jsx";
import {Outlet} from "react-router-dom";

function App() {
    return (
        <>
            <Nav/>
            <div className="container mx-auto">
                <Outlet/>
            </div>
        </>
    )
}

export default App
