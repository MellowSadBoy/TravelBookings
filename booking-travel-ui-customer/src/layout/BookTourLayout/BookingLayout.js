import React from 'react'
import classNames from 'classnames/bind';
import Header from './HeaderBooking/Header'
import Content from './ContentBooking/Content'
import styles from './BookTour.module.scss';

const cx = classNames.bind(styles);

function BookingLayout({infoBook, children}) {
    return (
        <div className={cx('wrapper')}>
            <Header infoBook={infoBook}/>
            <Content children={children}/>
        </div>
    )
}

export default BookingLayout;