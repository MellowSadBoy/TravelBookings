import React from "react";
import { Link } from "react-router-dom";

function UpdateButton(props) {
    return (
        <Link to={`/users/${props.id}/edit`}>
            <button>Cập Nhật</button>
        </Link>
    );
}

export default UpdateButton;
