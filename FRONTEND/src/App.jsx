import './App.css'
import Nav from "./components/nav.jsx";
import PostCard from "./components/postCard.jsx";
import PostSearch from "./components/postSearch.jsx";
//import LoginForm from "./components/loginForm.jsx";
// import PostForm from "./components/postForm.jsx";

function App() {

    return (
        <>
            <Nav/>
            <div className="container mx-auto">
                {/*<LoginForm/>*/}
                {/*<PostForm/>*/}
                <PostSearch/>
                    <PostCard/>
                    <PostCard/>
                    <PostCard/>
                    <PostCard/>
            </div>
        </>
    )
}

export default App
