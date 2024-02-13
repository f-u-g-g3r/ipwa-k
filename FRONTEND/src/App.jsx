import './App.css'
import Nav from "./components/partials/nav.jsx";
import {Outlet} from "react-router-dom";

function App() {

    function test(message) {
        console.log(message);
    }

    const test2 = (message) => {
        console.log(message);
    }

    test("message1");
    test2("message2");

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
