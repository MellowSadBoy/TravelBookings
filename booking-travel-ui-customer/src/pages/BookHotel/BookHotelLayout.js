import React, {useState} from 'react'

import BookingLayout from '~/layout/BookTourLayout/BookingLayout';
import BookHotel from './BookHotel';


function BookHotelLayout() {
    const [infoBook, setInfoBook] = useState(false);
    return (

    <div>
        <BookingLayout infoBook={infoBook} children={<BookHotel infoBook={infoBook} setInfoBook={setInfoBook}/>}/>
    </div>
  )
}

export default BookHotelLayout;