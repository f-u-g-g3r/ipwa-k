import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
    const error = useRouteError();
    console.error(error);

    return (
        <div className="container mx-auto my-20 text-center text-2xl" id="error-page">
            <h1 className="font-bold text-4xl">Oops!</h1>
            <p className="my-5">Sorry, an unexpected error has occurred.</p>
            <p>
                <i>{error.statusText || error.message}</i>
            </p>
        </div>
    );
}