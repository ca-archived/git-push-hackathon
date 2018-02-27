//
//  Date+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/08.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

// MARK: - String Extensions

extension Date {

    private var yearsFrom: Int {

        return Calendar.current.dateComponents([.year], from: self, to: Date()).year!
    }

    private var monthsFrom: Int {

        return Calendar.current.dateComponents([.month], from: self, to: Date()).month!
    }

    private var daysFrom: Int {

        return Calendar.current.dateComponents([.day], from: self, to: Date()).day!
    }

    private var hoursFrom: Int {

        return Calendar.current.dateComponents([.hour], from: self, to: Date()).hour!
    }

    private var minutesFrom: Int {

        return Calendar.current.dateComponents([.minute], from: self, to: Date()).minute!
    }

    private var secondsFrom: Int {

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

    var dateString: String {

        let dateFormat = DateFormatter()
        dateFormat.dateStyle = .short
        dateFormat.timeStyle = .none
        dateFormat.locale = Locale(identifier: "ja_JP")
        return dateFormat.string(from: self)
    }
}
