export const getStarArr = (value) => {
    let star = 0;
    switch (value) {
        case "FIVE_STAR":
            star = 5;
            break;
        case "FOUR_STAR":
            star = 4;
            break;
        case "THREE_STAR":
            star = 3;
            break;
        case "TWO_STAR":
            star = 2;
            break;
        case "ONE_STAR":
            star = 1;
            break;
        default:
            star = 0;
            break;
    }
    return Array.from(Array(star).keys());
};
export const getStarIcon = (value) => {
    let star ;
    // eslint-disable-next-line default-case
    switch (value) {
        case "FIVE_STAR":
            star = (<><i className="fa fa-star"/>
                <i className="fa fa-star"/>
                <i className="fa fa-star"/>
                <i className="fa fa-star"/>
                <i className="fa fa-star"/></>)
            ;
            break;
        case "FOUR_STAR":
            star = (<><i className="fa fa-star"/>
                <i className="fa fa-star"/>
                <i className="fa fa-star"/>
                <i className="fa fa-star"/>
            </>);
            break;
        case "THREE_STAR":
            star = (<><i className="fa fa-star"/>
                <i className="fa fa-star"/>
                <i className="fa fa-star"/>
            </>);
            break;
        case "TWO_STAR":
            star = (<><i className="fa fa-star"/>
                <i className="fa fa-star"/>
            </>);
            break;
        case "ONE_STAR":
            star = (<><i className="fa fa-star"/></>);
            break;
    }
    return star;
};

