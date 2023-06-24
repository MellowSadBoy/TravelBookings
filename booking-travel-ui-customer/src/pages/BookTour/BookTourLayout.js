import React, {useState} from 'react'

import BookingLayout from '~/layout/BookTourLayout/BookingLayout';
import BookTour from './BookTour';


function BookTourLayout() {
    const [infoBook, setInfoBook] = useState(false);
    return (
        <div>
            <BookingLayout infoBook={infoBook} children={<BookTour infoBook={infoBook} setInfoBook={setInfoBook}/>}/>
        </div>
    )
}

export default BookTourLayout;