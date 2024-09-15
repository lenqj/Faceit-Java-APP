import React from "react";
import {Avatar, TableRow, TableCell} from "@mui/material";
class UserItem extends React.Component {
    render() {
        const { user } = this.props;
        return (
            <TableRow hover key = {user.username} button onClick={() => window.location.href="/profile/" + user.username}>
                <TableCell>
                <Avatar>{user.username.charAt(0)}</Avatar>
                </TableCell>
                <TableCell>
                {user.username}
                </TableCell>
                <TableCell>{user.emailAddress}</TableCell>
                <TableCell>{user.firstName + " " + user.lastName}</TableCell>
            </TableRow>

        )
    }
}

export default UserItem;