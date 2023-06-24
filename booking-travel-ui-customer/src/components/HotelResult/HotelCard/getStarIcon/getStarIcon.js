import { Star } from '@material-ui/icons';

const rankNames = {
    ONE_STAR: '1 sao',
    TWO_STAR: '2 sao',
    THREE_STAR: '3 sao',
    FOUR_STAR: '4 sao',
    FIVE_STAR: '5 sao',
};
function getRankName(rank) {
    return rankNames[rank];
}

function getStarIcon(rank) {
    switch (getRankName(rank)) {
        case '1 sao':
            return <Star />;
        case '2 sao':
            return <>
                <Star />
                <Star />
            </>;
        case '3 sao':
            return <>
                <Star />
                <Star />
                <Star />
            </>;
        case '4 sao':
            return <>
                <Star />
                <Star />
                <Star />
                <Star />
            </>;
        case '5 sao':
            return <>
                <Star />
                <Star />
                <Star />
                <Star />
                <Star />
            </>;
        default:
            return null;
    }
}
export default getStarIcon;
