import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
    FormControl,
    Select,
    MenuItem,
    IconButton,
    Tooltip, Typography,
} from '@material-ui/core';
import {
    Sort as FilterListIcon,

} from '@material-ui/icons';


const useStyles = makeStyles((theme) => ({
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    iconButton: {
        padding: theme.spacing(1),
    },
}));

function Filter({ onSort }) {
    const classes = useStyles();
    const [sortBy, setSortBy] = useState('');

    const handleSortByChange = (event) => {
        const value = event.target.value;
        setSortBy(value);
        onSort(value);
    };

    return (
        <div>
            <Tooltip title="Filter">
                <IconButton className={classes.iconButton}>
                    <FilterListIcon />
                </IconButton>
            </Tooltip>

            <FormControl className={classes.formControl}>
                <Typography variant="h6">Sắp xếp theo</Typography>
                <Select value={sortBy} onChange={handleSortByChange}>
                    <MenuItem value="default">
                        Mặc định
                    </MenuItem>
                    <MenuItem value="rank-desc">
                        Theo hạng sao từ thấp - cao
                    </MenuItem>
                    <MenuItem value="rank-asc">
                         Theo hạng sao từ cao - thấp
                    </MenuItem>
                    <MenuItem value="price-asc">
                        Theo giá tiền từ thấp - cao
                    </MenuItem>
                    <MenuItem value="price-desc">
                         Theo giá tiền từ cao - thấp
                    </MenuItem>
                </Select>
            </FormControl>
        </div>
    );
}

export default Filter;
