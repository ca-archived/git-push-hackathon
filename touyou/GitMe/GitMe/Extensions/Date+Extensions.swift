//
//  Date+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/08.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

extension Date {

    var yearsFrom: Int {

        return Calendar.current.dateComponents([.year], from: self, to: Date()).year!
    }

    var monthsFrom: Int {

        return Calendar.current.dateComponents([.month], from: self, to: Date()).month!
    }

    var daysFrom: Int {

        return Calendar.current.dateComponents([.day], from: self, to: Date()).day!
    }

    var hoursFrom: Int {

        return Calendar.current.dateComponents([.hour], from: self, to: Date()).hour!
    }

    var minutesFrom: Int {

        return Calendar.current.dateComponents([.minute], from: self, to: Date()).minute!
    }

    var secondsFrom: Int {

        return Calendar.current.dateComponents([.second], from: self, to: Date()).second!
    }

    var offsetString: String {

        if self.yearsFrom > 0 {

            return "\(self.yearsFrom) years ago"
        }
        if self.monthsFrom > 0 {

            return "\(self.monthsFrom) months ago"
        }
        if self.daysFrom > 0 {

            return "\(self.daysFrom) days ago"
        }
        if self.hoursFrom > 0 {

            return "\(self.hoursFrom) hours ago"
        }
        if self.minutesFrom > 0 {

            return "\(self.minutesFrom) minutes ago"
        }
        if self.secondsFrom > 0 {

            return "\(self.secondsFrom) seconds ago"
        }
        return "now"
    }

}
