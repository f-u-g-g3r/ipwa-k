import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import App from "./App.jsx";
import ErrorPage from "./components/partials/error404.jsx";
import LoginForm, {action, loginLoader} from "./components/loginForm.jsx";
import Home from "./components/home.jsx";
import Posts from "./components/post/posts.jsx";
import PostForm, {actionPostForm} from "./components/post/postForm.jsx";
import {isAuthLoader} from "./components/services/AuthService.jsx";
import OnePost from "./components/post/onePost.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                path: "/login",
                loader: loginLoader,
                action: action,
                element: <LoginForm/>
            },
            {
                path: "/home",
                element: <Home/>
            },
            {
                path: "/posts",
                element: <Posts/>,
                loader: isAuthLoader
            },
            {
                path: "/posts/new",
                element: <PostForm/>,
                action: actionPostForm,
            },

            {
                path: "/posts/:id",
                element: <OnePost/>,
                loader: isAuthLoader
            },
        ]
    },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>,
)
