import React from "react";
import { TableCell, TableRow } from "@mui/material";

class TeamItem extends React.Component {
    render() {
        const { team } = this.props;
        const encodedName = team.name.replace(/ /g, "%20");

        return (
            <TableRow hover onClick={() => window.location.href="/team/" + encodedName}>
                <TableCell>
                    {team.name}
                </TableCell>
                <TableCell>
                    {team.teamLeader.username}
                </TableCell>
                <TableCell>{team.numberOfMatches}</TableCell>
            </TableRow>
        );
    }
}

export default TeamItem;
